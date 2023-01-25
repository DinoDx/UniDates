import flask
from flask import request, jsonify
import pandas as pd
import psycopg2
from sklearn.preprocessing import LabelEncoder
from sklearn.cluster import KMeans
import numpy as np

app = flask.Flask(__name__)
app.config["DEBUG"] = True


@app.route('/', methods=['GET'])
def home():
    query_parameters = request.args
    email = query_parameters.get('email')

    studente_scelto, lista_altri_studenti = seleziona_studenti(email)
    cluster_effettuato = effettua_cluster(studente_scelto, lista_altri_studenti)

    # viene ritornata una lista di id profilo per lo studente scelto, ne creo una stringa

    toreturn = ''

    for id in cluster_effettuato:
        toreturn += str(id) + " "
    return toreturn


def seleziona_studenti(email):
    connection = psycopg2.connect(
        host="localhost",
        database="spring_unidates",
        user="postgres",
        password="password",
        port="5432"
    )
    # seleziona lo studente passato con tutti gli hobby
    cursor = connection.cursor()

    cursor.execute('SELECT * FROM Utente as u, Profilo as p WHERE u.profilo_id = p.id')
    tuttiGliStudenti = cursor.fetchall()
    listaStudenti = []
    studenteScelto = {}

    for studente in tuttiGliStudenti:
        hobbyListStudente = []
        cursor.execute("SELECT * FROM Profilo_hobby_list as phl WHERE phl.profilo_id =" + str(studente[8]))

        for hobby in cursor.fetchall():
            hobbyListStudente.append(hobby[1])
        studenteCompleto = {
            "studente": list(studente),
            "hobbies": hobbyListStudente
        }
        if studenteCompleto['studente'][1] == email:
            studenteScelto = studenteCompleto
        else:
            listaStudenti.append(studenteCompleto)

    return studenteScelto, listaStudenti


def effettua_cluster(studente_scelto, altri_studenti):
    pd.set_option("display.max.columns", None)

    listaAltezze = []
    listaDate = []
    listHobby = []
    listaId = []

    # 15 interesse, 21 sesso
    # 1 = DONNE
    # 0 = UOMINI
    # 2 = ENTRAMBI // ALTRO

    # Aggiungo gli studenti al dataset in base agli interessi dello scelto
    for studente in altri_studenti:
        if (studente_scelto['studente'][15] != 2 and studente['studente'][15] != 2 and studente_scelto['studente'][
            15] ==
            studente['studente'][21] and studente_scelto['studente'][21] == studente['studente'][15]) or \
                (studente_scelto['studente'][15] == 2 and studente['studente'][15] != 2 and studente_scelto['studente'][
                    21] ==
                 studente['studente'][15]) or \
                (studente_scelto['studente'][15] != 2 and studente['studente'][15] == 2 and studente_scelto['studente'][
                    15] ==
                 studente['studente'][21]) or \
                (studente_scelto['studente'][15] == 2 and studente['studente'][15] == 2):
            listaId.append(studente["studente"][9])
            listaAltezze.append(studente["studente"][10])
            listaDate.append(str(studente["studente"][14])[0:4])
            listHobby.append(studente["hobbies"])

    # Aggiungo lo studente scelto al dataset
    listaId.append(studente_scelto["studente"][9])
    listaAltezze.append(studente_scelto["studente"][10])
    listaDate.append(str(studente_scelto["studente"][14])[0:4])
    listHobby.append(studente_scelto["hobbies"])

    df = pd.DataFrame(listaAltezze, columns=['altezza'])
    df['date'] = listaDate
    df['hobby'] = listHobby

    hobbies_df = pd.DataFrame(df['hobby'].tolist())
    hobbies_obj = hobbies_df.stack()
    hobbies_df = pd.get_dummies(hobbies_obj)
    hobbies_df = hobbies_df.sum(level=0)

    df = pd.concat([df, hobbies_df], axis=1)
    df = df.drop('hobby', axis=1)

    kmeans = KMeans(n_clusters=6)
    kmeans.fit(df)

    df['cluster'] = kmeans.labels_
    df['idProfilo'] = listaId

    listaUtenti = []
    cluster = df['cluster'].tolist()
    for index in range(len(cluster)):
        listaUtenti.append({
            'id_profilo': listaId[index],
            'cluster': cluster[index]
        })

    scelto = listaUtenti.pop()

    toReturn = []

    # suggerisce un massimo di 15 utenti dello stesso cluster
    for utente in listaUtenti:
        if utente['cluster'] == scelto['cluster'] and len(toReturn) < 15:
            toReturn.append(utente['id_profilo'])
            listaUtenti.remove(utente)
        elif len(toReturn) >= 15:
            break

    # suggerisce 5 utenti di un diverso cluster
    for utente in listaUtenti:
        if (utente['cluster'] == scelto['cluster'] + 1 or utente['cluster'] == scelto['cluster'] - 1) and len(
                toReturn) < 20:
            toReturn.append(utente['id_profilo'])
            listaUtenti.remove(utente)
        elif len(toReturn) >= 20:
            break

    return toReturn


app.run()

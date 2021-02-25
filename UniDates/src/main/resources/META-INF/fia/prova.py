import flask
from flask import request, jsonify
import pandas as pd
import psycopg2
from sklearn.preprocessing import LabelEncoder
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
import numpy as np
from random import randint
from random import random
from sklearn import metrics

app = flask.Flask(__name__)
app.config["DEBUG"] = True


@app.route('/', methods=['GET'])
def home():
    query_parameters = request.args
    email = query_parameters.get('email')
    toreturn = ""
    for id in query(email):
        toreturn += str(id) + " "
    return toreturn


def query(email):
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

    listaStudenti.append(studenteCompleto)
    return cluster(studenteScelto, listaStudenti)


def cluster(scelto, altri):
    pd.set_option("display.max.columns", None)
    listaAltezze = []
    listaDate = []
    listHobby = []
    listaId = []

    ## lo studente scelto è già nella lista
    for studente in altri:
        listaId.append(studente["studente"][9])
        listaAltezze.append(studente["studente"][10])
        listaDate.append(str(studente["studente"][14])[0:4])
        listHobby.append(studente["hobbies"])

    df = pd.DataFrame(listaAltezze, columns=['altezza'])
    df['date'] = listaDate
    df['hobby'] = listHobby

    hobbies_df = pd.DataFrame(df['hobby'].tolist())
    hobbies_obj = hobbies_df.stack()
    hobbies_df = pd.get_dummies(hobbies_obj)
    hobbies_df = hobbies_df.sum(level=0)
    df = pd.concat([df, hobbies_df], axis=1)
    df = df.drop('hobby', axis=1)




    labelEncoder = LabelEncoder()
    labelEncoder.fit(df['altezza'])
    df['altezza'] = labelEncoder.transform(df['altezza'])

    """
    for column in df:
        labelEncoder.fit(df[column])
        df[column] = labelEncoder.transform(df[column])
    """

    kmeans = KMeans(n_clusters=4)
    kmeans.fit(df)

    df['cluster'] = kmeans.labels_
    df['idProfilo'] = listaId

    label = kmeans.labels_
    u_labels = np.unique(label)


    """
    for i in u_labels:
        filtered = df[label == i]
        plt.scatter(filtered['date'], filtered['altezza'])
    plt.show()
    """

    listaUtenti = []
    cluster = df['cluster'].tolist()
    for index in range(len(cluster)):
        listaUtenti.append({
            'id_profilo': listaId[index],
            'cluster' : cluster[index]
        })

    scelto = listaUtenti.pop()

    toReturn = []

    for utente in listaUtenti:
        if utente['cluster'] == scelto['cluster']:
            toReturn.append(utente['id_profilo'])

    return toReturn

    """
    for i in range(len(cluster)):
        if cluster[i] == clusterScelto :
            listaUtenti.append(i)
    """




app.run()

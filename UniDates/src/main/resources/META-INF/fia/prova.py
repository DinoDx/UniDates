import flask
from flask import request, jsonify
import psycopg2

app = flask.Flask(__name__)
app.config["DEBUG"] = True


@app.route('/', methods=['GET'])
def home():
    query_parameters = request.args
    email = query_parameters.get('email')
    tryquery(email)
    return "ciao"

def tryquery(email):
    connection = psycopg2.connect(
        host="localhost",
        database="spring_unidates",
        user="postgres",
        password="password",
        port="5432"
        )

    cursor = connection.cursor()
    email = "'" + email + "'"
    query = 'SELECT * FROM Utente as u, Profilo as p WHERE email = ' +  email + 'and u.profilo_id = p.id'
    cursor.execute(query)

    print(cursor.fetchall())
    
    cursor.close()
    connection.close()


app.run()
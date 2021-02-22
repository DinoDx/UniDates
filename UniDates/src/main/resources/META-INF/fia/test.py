import json
import pandas as pd
import numpy as np
from sklearn.preprocessing import LabelEncoder
from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
from sklearn import metrics


#import numpy as np
#with open("csvjson.json") as f:
#   data = json.load(f)

#print(data[0:5])

pd.set_option("display.max.columns", None)
nba = pd.read_csv("UniDates.csv")
 
#Prendo tutti gli interessi
arrayInteressi = np.array(nba["Quali sono i tuoi interessi? (possibile scelta multipla)"])

#Rimuovo tutte le colonne inutili
toDelete = ["Informazioni cronologiche","Sei interessato a.. (possibile scelta multipla)", "Qual è il colore dei tuoi occhi?",
"Qual è il colore dei tuoi capelli?","Quanto sei alto?","Cosa è importante per te in una persona? (possibile scelta multipla)","Quanto reputi utile un sito dating?","Se si, frequenti..", "Per cosa useresti il nostro sito di dating?", "Quali sono i tuoi interessi? (possibile scelta multipla)"]
toTest = nba.drop(toDelete, axis=1)

#Codifica le stringhe in interi
labelEncoder = LabelEncoder()
for column in toTest:
    labelEncoder.fit(toTest[column])
    toTest[column] = labelEncoder.transform(toTest[column])



#Aggiungo la l'array di Topic al DataSet
arrayTopic = []
allTopics =[]
for i in arrayInteressi:
    topicSingoloUtente = i.split(";")
    for topic in topicSingoloUtente:
        if topic not in allTopics:
            allTopics.append(topic)
    arrayTopic.append(topicSingoloUtente)


#Creo una matrice di array di booleani per ogni topic presente
booleanMatrix = []
for lista in arrayTopic:
    booleanArray = []
    for i in range(len(allTopics)):
        if allTopics[i] in lista:
            booleanArray.append(1)
        else:
            booleanArray.append(0)
    booleanMatrix.append(booleanArray)

#Aggiunge per ogni topic una colonna alla tabella e se il topic é stato scelto dall'utente, viene messo 1 nella casella corrispondente
for topic in allTopics:
    toTest[topic] = 0

counter = 0
for topic in allTopics:
    for i in range(len(allTopics)):
        toTest[topic][i] = booleanMatrix[i][counter]
    counter+=1

#effettuo il cluster con 10 clusters (casuale)
kmeans = KMeans(n_clusters = 10)
kmeans.fit(toTest)

toTest["cluster"] = kmeans.labels_

#calcolo il miglior indice di Silhouette per ottenere il numero di cluster da utilizzare
k_to_test = range(2,40,1) 
silhouette_scores = {}

for k in k_to_test:
    model_kmeans_k = KMeans( n_clusters = k )
    model_kmeans_k.fit(toTest.drop("cluster", axis = 1))
    labels_k = model_kmeans_k.labels_
    score_k = metrics.silhouette_score(toTest.drop("cluster", axis=1), labels_k)
    silhouette_scores[k] = score_k
    print("Testato il K-Means con k =%d\tIndice di silhouette: %5.4f" % (k, score_k))



#u_labels = np.unique(label)

#for i in u_labels:
#    plt.scatter(toTest[label == i, 0], toTest[label == i, 1], label= i )
#plt.legend()
#plt.show()




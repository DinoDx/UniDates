import json
import pandas as pd
import numpy as np
from sklearn.preprocessing import LabelEncoder
from sklearn.cluster import KMeans
from sklearn.decomposition import PCA
import matplotlib.pyplot as plt
from random import randint
from random import random
from sklearn import metrics
import seaborn as sns

# import numpy as np
# with open("csvjson.json") as f:
#   data = json.load(f)

# print(data[0:5])


pd.set_option("display.max.columns", None)
nba = pd.read_csv("UniDates.csv")

# Prendo tutti gli interessi
arrayInteressi = np.array(nba["Quali sono i tuoi interessi? (possibile scelta multipla)"])

# Rimuovo tutte le colonne inutili
toDelete = ["Informazioni cronologiche", "Sei interessato a.. (possibile scelta multipla)",
            "Cosa è importante per te in una persona? (possibile scelta multipla)",
            "Quanto reputi utile un sito dating?", "Se si, frequenti..", "Per cosa useresti il nostro sito di dating?",
            "Quali sono i tuoi interessi? (possibile scelta multipla)"]
toTest = nba.drop(toDelete, axis=1)

# Codifica le stringhe in interi
labelEncoder = LabelEncoder()
for column in toTest:
    if column == "Quanti anni hai?":
        for i in range(440):
            if toTest[column][i] == "18 - 25":
                toTest[column][i] = randint(18, 25)
            elif toTest[column][i] == "26 - 35":
                toTest[column][i] = randint(26, 35)
            elif toTest[column][i] == "36 - 45":
                toTest[column][i] = randint(36, 45)
            elif toTest[column][i] == "45 - 55":
                toTest[column][i] = randint(45, 55)
            elif toTest[column][i] == "Più di 55":
                toTest[column][i] = randint(56, 70)
            elif toTest[column][i] == "Meno di 18":
                toTest[column][i] = randint(10, 17)
    else:
        labelEncoder.fit(toTest[column])
        toTest[column] = labelEncoder.transform(toTest[column])

# Aggiungo la l'array di Topic al DataSet
arrayTopic = []
allTopics = []
for i in arrayInteressi:
    topicSingoloUtente = i.split(";")
    for topic in topicSingoloUtente:
        if topic not in allTopics:
            allTopics.append(topic)
    arrayTopic.append(topicSingoloUtente)

# Creo una matrice di array di booleani per ogni topic presente
booleanMatrix = []
for lista in arrayTopic:
    booleanArray = []
    for i in range(len(allTopics)):
        if allTopics[i] in lista:
            booleanArray.append(1)
        else:
            booleanArray.append(0)
    booleanMatrix.append(booleanArray)

# Aggiunge per ogni topic una colonna alla tabella e se il topic é stato scelto dall'utente, viene messo 1 nella casella corrispondente
for topic in allTopics:
    toTest[topic] = 0

counter = 0
for topic in allTopics:
    for i in range(440):
        toTest[topic][i] = booleanMatrix[i][counter]
    counter += 1

# kmeans con indice di silhouette


k_to_test = range(2, 40, 1)
silhouette_scores = {}

toTest["cluster"] = 0
inertia = []
for k in k_to_test:
    model_kmeans_k = KMeans(n_clusters=k)
    model_kmeans_k.fit(toTest.drop("cluster", axis=1))
    labels_k = model_kmeans_k.labels_
    score_k = metrics.silhouette_score(toTest.drop("cluster", axis=1), labels_k)
    silhouette_scores[k] = score_k
    inertia.append(model_kmeans_k.inertia_)
    print("Testato il K-Means con k =%d\tIndice di silhouette: %5.4f" % (k, score_k))


kmeans = KMeans(n_clusters=4)
kmeans.fit(toTest)
label = kmeans.labels_
u_labels = np.unique(label)

toTest["cluster"] = kmeans.labels_
toTest["combinazione"] = toTest["Quanti anni hai?"] * random() * 0.2 + toTest["Sei.."] * toTest[
    "Quanto sei alto?"] * random() * toTest["Qual è il colore dei tuoi occhi?"] * toTest[
                             "Qual è il colore dei tuoi capelli?"]

for i in u_labels:
    filtered = toTest[label == i]
    plt.scatter(filtered["combinazione"], filtered["Quanti anni hai?"])
plt.show()

plt.plot(k_to_test, inertia, 'bx-')
plt.xlabel('k')
plt.ylabel('Inertia')
plt.show()

"""
pca = PCA(2)
pca.fit(toTest)
pca_data = pd.DataFrame(pca.transform(toTest))
print(pca_data.head())

kmeans.fit(pca_data)
label = kmeans.labels_
for i in np.unique(label):
    filtered = pca_data[label == i]
    plt.scatter(filtered[0], filtered[1])
plt.show()
"""

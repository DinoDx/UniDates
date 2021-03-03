import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

from random import randint
from sklearn.cluster import KMeans
from sklearn import metrics

pd.set_option("display.max.columns", None)
nba = pd.read_csv("UniDates.csv")

sizeCSV = len(nba["Informazioni cronologiche"])

# Prendo tutti gli interessi

# Rimuovo tutte le colonne inutili
toDelete = ["Informazioni cronologiche", "Sei interessato a.. (possibile scelta multipla)",
            "Cosa è importante per te in una persona? (possibile scelta multipla)",
            "Quanto reputi utile un sito dating?", "Se si, frequenti..",
            "Per cosa useresti il nostro sito di dating?",
            "Qual è il colore dei tuoi occhi?", "Qual è il colore dei tuoi capelli?", "Studi?", "Sei.."]

toTest = nba.drop(toDelete, axis=1)

for i in range(len(toTest["Quali sono i tuoi interessi? (possibile scelta multipla)"])):
    toTest["Quali sono i tuoi interessi? (possibile scelta multipla)"][i] = \
        toTest["Quali sono i tuoi interessi? (possibile scelta multipla)"][i].split(";")

hobbies_df = pd.DataFrame(toTest["Quali sono i tuoi interessi? (possibile scelta multipla)"].tolist())
hobbies_obj = hobbies_df.stack()
hobbies_df = pd.get_dummies(hobbies_obj)
hobbies_df = hobbies_df.sum(level=0)
toTest = pd.concat([toTest, hobbies_df], axis=1)
toTest = toTest.drop("Quali sono i tuoi interessi? (possibile scelta multipla)", axis=1)

for column in toTest:
    if column == "Quanti anni hai?":
        for i in range(sizeCSV):
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
    elif column == "Quanto sei alto?":
        for i in range(sizeCSV):
            if toTest[column][i] == "Meno di 1,40m":
                toTest[column][i] = randint(131, 140)
            elif toTest[column][i] == "1,41m - 1,60m":
                toTest[column][i] = randint(141, 160)
            elif toTest[column][i] == "1,61m - 1,80m":
                toTest[column][i] = randint(161, 180)
            elif toTest[column][i] == "1,81m - 2m":
                toTest[column][i] = randint(181, 200)
            elif toTest[column][i] == "Più di 2m":
                toTest[column][i] = randint(200, 210)

k_to_test = range(2, 40, 1)

plt.scatter(toTest["Quanto sei alto?"], toTest["Quanti anni hai?"])
plt.show()

toTest["cluster"] = 0
best_number = 0
best_score = 0

silhouette_scores = []
inertia = []

for k in k_to_test:
    model_kmeans_k = KMeans(n_clusters=k)
    model_kmeans_k.fit(toTest.drop("cluster", axis=1))
    labels_k = model_kmeans_k.labels_
    score_k = metrics.silhouette_score(toTest.drop("cluster", axis=1), labels_k)
    silhouette_scores.append(score_k)
    inertia.append(model_kmeans_k.inertia_)
    if score_k > best_score:
        best_score = score_k
        best_number = k

plt.plot(k_to_test, inertia, 'bx-')
plt.xlabel('k')
plt.ylabel('Inertia')
plt.show()

plt.plot(k_to_test, silhouette_scores, 'bp-')
plt.xlabel("k")
plt.ylabel("Indice di forma")
plt.show()

# print("Testato il K-Means con k =%d\tIndice di silhouette: %5.4f" % (k, score_k))

print("Il cluster migliora avviene con: ", best_number, " clusters")

kmeans = KMeans(n_clusters=best_number)
kmeans.fit(toTest)
label = kmeans.labels_
u_labels = np.unique(label)

toTest["cluster"] = kmeans.labels_
for i in u_labels:
    filtered = toTest[label == i]
    plt.scatter(filtered["Quanto sei alto?"],
                filtered["Quanti anni hai?"])
plt.show()

# print(k_to_test, " ", len(k_to_test))
# print(silhouette_scores, " ", len(silhouette_scores))

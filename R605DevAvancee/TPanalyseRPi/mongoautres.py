import pymongo
from datetime import datetime, timedelta

client = pymongo.MongoClient("mongodb://etudiant:etudiant@192.168.25.23:27017/tp")
db = client.tp

def emprunterLivre(isbn, nom, groupe):
    livre = db.livres.find_one({"isbn": isbn, "disponible": True})
    if not livre:
        print("ERREUR : Livre non disponible")
        return
    db.livres.update_one(
        {"_id": livre['_id']},
        {"$set": {"disponible": False, "emprunteur": nom}}
    )
    db.emprunts.insert_one({
        "isbn_livre": isbn,
        "titre_livre": livre["titre"],
        "nom_etudiant": nom,
        "groupe": groupe,
        "date_emprunt": datetime.now(),
        "date_retour_prevue": datetime.now() + timedelta(days=7),
        "rendu": False
    })
    print("Livre emprunté avec succès")


def rendreLivre(isbn, nom, groupe):
    db.livres.update_one(
        {"isbn": isbn, "emprunteur": nom},
        {"$set": {"disponible": True, "emprunteur": None}}
    )
    db.emprunts.update_one(
        {"isbn_livre": isbn, "rendu": False, "nom_etudiant": nom, "groupe": groupe},
        {"$set": {"rendu": True}}
    )

def stat() :
    groups = db.emprunts.distinct("groupe")
    for x in groups :
        print("Groupe "+str(x)+" :")
        print(" Nombre total d'emprunts : "+str(db.emprunts.count_documents({"groupe":x})))
        print(" Nombre d'étudiants actifs : "+ str(len(db.emprunts.distinct('nom_etudiant'))))
        print(" Livres différents empruntés : " + str(len(db.emprunts.distinct('isbn_livre'))))

    print("Statistiques générales : ")
    print(" Nombre total de livres : "+ str(db.livres.count_documents({})))
    print(" Nombre total d'emprunts : "+ str(db.emprunts.count_documents({})))
    tot = db.livres.count_documents({})
    util = db.livres.count_documents({"disponible":False})
    print(" Taux d'utilisation actuel des livres : "+ str((util/tot)*100)+"%")


stat()
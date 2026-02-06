const produits = [];

module.exports = class Produit{
    constructor(produit){
        this.produit = produit;
    }

    save(){
        produits.push(this);
    }

    static fetchAll(){
        return produits;
    }
}
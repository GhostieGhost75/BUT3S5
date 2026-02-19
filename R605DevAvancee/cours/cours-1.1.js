const coef1=[0,1,2];
const coef2=coef1.slice();
const coef3 = coef1;
console.log(coef2);
console.log(coef3);
const coef4 = [...coef1];
console.log(coef4);

const personne={
  name: "toto",
  age: 25,
  profil: () => {
    console.log("nom : "+personne.name+" et age : "+personne.age);
  }
}
personne.profil();

const personne2={...personne}

console.log(personne2)

const toArray=(a,b,c) => {
  return([a,b,c]);
}
console.log(toArray(1,2,3));

const toArray2=(...args)=>{
  return(args);
}

console.log(toArray2(4,5,6));
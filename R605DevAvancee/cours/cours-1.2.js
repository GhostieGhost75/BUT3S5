const personne={
  name: "toto",
  age: 25,
  profil() {
    console.log("nom : "+this.name+" et age : "+this.age);
  }
}

const printName = function (personneData){
  console.log(personneData.name)
}

printName(personne);

const personne2={
  name: "titi",
  age: 30,
  profil() {
    console.log("nom : "+this.name+" et age : "+this.age);
  }
}

printName(personne2);

const printName2=( { name })=>{
  console.log(name);
}

printName2(personne2);

const personne3={
  name: "titi",
  age: 30,
  profil() {
    console.log("nom : "+this.name+" et age : "+this.age);
  }
}

const { name, age} = personne3;
console.log(name, age)

const ue = ["anglais","maths","informatique"];

const [ue1,ue2] = ue;

console.log(ue1,ue2)

const finish = () => {console.log("finish !")}

const fetchData = () => {
  const promise = new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve('OK');
    }, 1500);
  });
  return promise;
};

setTimeout(()=>{
  console.log("finish !");
  fetchData()
    .then(text => {
      console.log(text);
      return fetchData(text);
    })
    .then((text2) => {
      console.log(text2);
    });
}, 2000);


console.log("bonjour");
console.log("prof");
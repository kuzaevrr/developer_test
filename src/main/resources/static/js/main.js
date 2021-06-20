
const table = document.querySelector('table'),
      table_body = table.querySelectorAll('.table_tr');

table_body.forEach((item, i) =>{
    if(i%2){
        //нечетные
        item.classList.add("even")
    }else{
        item.classList.add("odd")
    }
});




let requestURL = 'http://localhost:8080/api/allEmployees';
let request = new XMLHttpRequest();
request.open('GET', requestURL);
request.responseType = 'json';
request.send();
request.onload = function() {
    let superHeroesText = request.response; // get the string from the response
    // let superHeroes = JSON.parse(superHeroesText); // convert it to an object
    console.log(superHeroesText);
}


window.addEventListener("DOMContentLoaded", () => {

    const buttonTask = document.querySelector(".btn_task "),
        buttonEmployee = document.querySelector(".btn_emp"),
        tableEmp = document.querySelector(".table_emps"),
        tableTask = document.querySelector(".table_tasks"),
        urlBase = "http://localhost:8080";


    loadAndCreateTable(urlBase + "/api/allEmployees", "table_emps");
    clickable_sort(document.querySelectorAll(".table"),
        document.querySelectorAll(".image_sort"));

    buttonEmployee.setAttribute("disabled", true);

    buttonTask.addEventListener("click", () => {
        loadAndCreateTable(urlBase + "/api/allTasks", "table_tasks");
        buttonTask.setAttribute("disabled", true);
        buttonEmployee.removeAttribute("disabled");
    });

    buttonEmployee.addEventListener("click", () => {
        loadAndCreateTable(urlBase + "/api/allEmployees", "table_emps");
        buttonEmployee.setAttribute("disabled", true);
        buttonTask.removeAttribute("disabled");
    });


    function clickable_sort(buttonTable, buttonSort) {
        buttonTable.forEach(item => {
            let length = 1;
            let numb = [];
            item.addEventListener("click", (event) => {
                const target = event.target;
                if (target && target.classList.contains("image_sort")) {
                    buttonSort.forEach((item, i) => {
                        if (length) {
                            numb.push(1);
                            length++;
                            if (length === buttonSort.length + 1) {
                                length = 0;
                            }

                        }
                        if (target === item) {
                            numb[i]++;
                            if (numb[i] === 1) {
                                item.src = 'images/sort.png';

                            } else if (numb[i] === 2) {
                                item.src = 'images/up.png';
                                let xhttp = new XMLHttpRequest();
                                xhttp.open("GET", urlBase + "/api/" + "get_sort_up", true);
                                xhttp.send();
                                xhttp.onload = function() { // (3)

                                    if (xhttp.status != 200) {
                                        alert(xhttp.status + ': ' + xhttp.statusText);
                                    } else {
                                        console.log(xhttp.response);
                                    }

                                }
                                // createTable(xhttp, "table_emps");


                            } else if (numb[i] === 3) {
                                item.src = 'images/down.png';
                                numb[i] = 0;
                            }
                            console.log(numb.length);
                            console.log(numb);
                        }
                    });
                }
            });
        });
    }


    function tableView() {
        const table_body = document.querySelectorAll('.table_tr');

        table_body.forEach((item, i) => {
            if (i % 2) {
                //четные
                item.classList.add("even")
            } else {
                //нечетные
                item.classList.add("odd")
            }
        });
    }

    function createTable(data, tableName) {
        let docRemove = document.querySelectorAll("." + tableName + " .table_tr");
        if (docRemove != null) {
            docRemove.forEach(item => {
                item.remove();
            });
        }
        let table = "";

        data.forEach(item => {

            if (tableName === "table_emps") {

                visible(true);
                if (item.leaderName == null) {
                    item.leaderName = "";
                }
                table +=
                    "<tr class=\"table_tr\">" +
                    "<td class=\"table_th\">" + item.id + "</td>" +
                    "<td class=\"table_th\">" + item.full_name + "</td>" +
                    "<td class=\"table_th\">" + item.leaderName + "</td>" +
                    "<td class=\"table_th\">" + item.branch_name + "</td>" +
                    "<td class=\"table_th\">" + item.numberTasks + "</td>" +
                    "</tr>";
            } else if (tableName === "table_tasks") {

                visible(false);
                table +=
                    "<tr class=\"table_tr\">" +
                    "<td class=\"table_th\">" + item.id + "</td>" +
                    "<td class=\"table_th\">" + item.description + "</td>" +
                    "<td class=\"table_th\">" + item.nameEmployee + "</td>" +
                    "<td class=\"table_th\">" + item.priority + "</td>" +
                    "</tr>";
            }
        });
        document.querySelector("." + tableName).insertAdjacentHTML("beforeEnd", table);
        tableView();
    }

    function loadAndCreateTable(url, tableName) {
        $(document).ready(function () {
            $.get(url, function (data) {
                createTable(data, tableName);
            });
        });
    }

    function visible(bool) {
        let arr = ["show", "none"];
        tableEmp.classList.add(arr[+!bool]);
        tableTask.classList.remove(arr[+!bool]);
        tableTask.classList.add(arr[+bool]);
        tableEmp.classList.remove(arr[+bool]);
    }

});


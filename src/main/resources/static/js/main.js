window.addEventListener("DOMContentLoaded", () => {

    const buttonTask = document.querySelector(".btn_task "),
        buttonEmployee = document.querySelector(".btn_emp"),
        tableEmp = document.querySelector(".table_emps"),
        tableTask = document.querySelector(".table_tasks"),
        urlBase = "http://localhost:8080";

    function clickable_sort(buttonTable, buttonSort) {
        buttonTable.forEach(item =>{
            item.addEventListener("click", (event) => {
                const target = event.target;
                if (target && target.classList.contains("image_sort")) {
                    buttonSort.forEach((item, i) => {
                        if (target == item) {
                            console.log(i);
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


    function createTable(url, tableName) {
        $(document).ready(function () {
            $.get(url, function (data) {

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



    createTable(urlBase + "/api/allEmployees", "table_emps");
    clickable_sort(document.querySelectorAll(".table"),
        document.querySelectorAll(".image_sort"));

    buttonEmployee.setAttribute("disabled", true);

    buttonTask.addEventListener("click", () => {
        createTable(urlBase + "/api/allTasks", "table_tasks");
        buttonTask.setAttribute("disabled", true);
        buttonEmployee.removeAttribute("disabled");
    });

    buttonEmployee.addEventListener("click", () => {
        createTable(urlBase + "/api/allEmployees", "table_emps");
        buttonEmployee.setAttribute("disabled", true);
        buttonTask.removeAttribute("disabled");
    });


});


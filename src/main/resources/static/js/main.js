window.addEventListener("DOMContentLoaded", () => {
    const buttonTask = document.querySelector(".btn_task "),
        buttonEmployee = document.querySelector(".btn_emp"),
        tableEmp = document.querySelector(".table_emps"),
        tableTask = document.querySelector(".table_tasks"),
        urlBase = "http://localhost:8080";

    function tableView() {
        const table_body = document.querySelectorAll('.table_tr');

        table_body.forEach((item, i) => {
            if (i % 2) {
                //нечетные
                item.classList.add("even")
            } else {
                item.classList.add("odd")
            }
        });
    }

    createTable(urlBase + "/api/allEmployees", ["ID", "ФИО", "Руководитель", "Филиал", "Количество задач"], "table_emps");

    function createTable(url, arrTitle, tableName) {
        $(document).ready(function () {
            $.get(url, function (data) {
                console.log(data);

                let table =
                    "<tr class=\"table_tr_root\">\n" +
                    "<th class=\"table_th\">" + arrTitle[0] + "</th>\n" +
                    "<th class=\"table_th\">" + arrTitle[1] + "</th>\n" +
                    "<th class=\"table_th\">" + arrTitle[2] + "</th>\n" +
                    "<th class=\"table_th\">" + arrTitle[3] + "</th>\n";

                if (tableName == "table_emps") {
                    table += "<th class=\"table_th\">" + arrTitle[4] + "</th>\n";
                }
                table += "</tr>";

                data.forEach(item => {

                    if (tableName == "table_emps") {
                        if (item.leaderName == null) {
                            item.leaderName = "";
                        }

                        table +=
                            "<tr class=\"table_tr\">" +
                            "<td class=\"table_th\">" + item.id + "</td>" +
                            "<td class=\"table_th\"> " + item.full_name + "</td>" +
                            "<td class=\"table_th\">" + item.leaderName + "</td>" +
                            "<td class=\"table_th\">" + item.branch_name + "</td>" +
                            "<td class=\"table_th\">" + item.numberTasks + "</td>" +
                            "</tr>";
                    } else if (tableName == "table_tasks") {
                        table +=
                            "<tr class=\"table_tr\">" +
                            "<td class=\"table_th\">" + item.id + "</td>" +
                            "<td class=\"table_th\"> " + item.description + "</td>" +
                            "<td class=\"table_th\">" + item.nameEmployee + "</td>" +
                            "<td class=\"table_th\">" + item.priority + "</td>" +
                            "</tr>";
                    }
                });
                $("." + tableName).html(table);
                tableView();
            });
        });
    }


    buttonTask.addEventListener("click", () => {
        createTable(urlBase + "/api/allTasks", ["ID", "Название", "Исполнитель", "Приоритет"], "table_tasks");
        tableEmp.classList.add("none");
        tableEmp.classList.remove("show");
        tableTask.classList.remove("none");
        tableTask.classList.add("show");

    });

    buttonEmployee.addEventListener("click", () => {
        createTable(urlBase + "/api/allEmployees", ["ID", "ФИО", "Руководитель", "Филиал", "Количество задач"], "table_emps");
        tableEmp.classList.add("show");
        tableEmp.classList.remove("none");
        tableTask.classList.remove("show");
        tableTask.classList.add("none");

    });
});


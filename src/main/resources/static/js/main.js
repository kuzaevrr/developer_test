'use strict';

window.addEventListener("DOMContentLoaded", () => {

    const buttonTask = document.querySelector(".btn_task "),
        buttonEmployee = document.querySelector(".btn_emp"),
        tableEmp = document.querySelector(".table_emps"),
        tableTask = document.querySelector(".table_tasks"),
        urlBase = "http://localhost:8080",

        btnAdd = document.querySelectorAll(".btn_add"),
        btnAddEmp = document.querySelector(".btn_add_emp"),
        btnAddTask = document.querySelector(".btn_add_task"),

        modalEmp = document.querySelector(".modal_emp"),
        modalTask = document.querySelector(".modal_task"),
        table = document.querySelectorAll('.table'),
        btnFormClose = document.querySelectorAll(".btn_form_close"),
        form = document.querySelectorAll('form');

    form.forEach((item) => {
        item.addEventListener('submit', (e) => {
            e.preventDefault();
        });
    });

    let arrEmployees = [],
        arrTasks = [];


    //load table
    loadAndCreateTable(urlBase + "/api/allEmployees", "table_emps");

    clickableSort(table,
        document.querySelectorAll(".image_sort"));


    //Редактирование сотрудника
    const tab_emp = document.querySelector('#tb_emp'),
        tab_task = document.querySelector('#tb_task');

    table.forEach(item => {
        item.onclick = function (event) {
            if (event.target.nodeName !== 'A') return;
            let urlPlus;
            let id = event.target.getAttribute('href');
            let resultObj;
            if (item.contains(tab_emp)) {
                urlPlus = `/api/getEmployee/${id}`;
            } else if (item.contains(tab_task)) {
                urlPlus = `/api/getTask/${id}`;
            }
            const request = new XMLHttpRequest;
            request.open('GET', urlBase + urlPlus);
            request.send();
            request.addEventListener('load', () => {
                if (request.status === 200) {
                    let object = JSON.parse(request.response);
                    if (item.contains(tab_emp)) {
                        resultObj = new Employee(
                            object.id,
                            object.fullName,
                            object.leader,
                            object.branchName,
                            object.numberTasks);
                    } else if (item.contains(tab_task)) {
                        resultObj = new Task(
                            object.id,
                            object.description,
                            object.employeeId,
                            object.priority);
                    }
                    showModalForm(item, resultObj);
                    console.log(`Employees -> ${resultObj instanceof Employee} \n
                                Task -> ${resultObj instanceof Task}`);
                } else {

                }
            });
            // console.log(href); // может быть подгрузка с сервера, генерация интерфейса и т.п.
            return false;
        };
    });


    //Редактирование задачи


    buttonEmployee.setAttribute("disabled", true);
    startVisibleBtnAdd();

    btnFormClose.forEach((item) => {
        item.addEventListener("click", () => {
            modalEmp.style.display = 'none';
            modalTask.style.display = 'none';
            document.body.style.overflow = '';
        });
    });

    buttonTask.addEventListener("click", () => {
        loadAndCreateTable(urlBase + "/api/allTasks", "table_tasks");
        buttonTask.setAttribute("disabled", true);
        buttonEmployee.removeAttribute("disabled");
        reverseVisibleBtnAdd(btnAddTask, btnAddEmp);
        // document.body.style.overflow = 'hidden';
    });

    buttonEmployee.addEventListener("click", () => {
        loadAndCreateTable(urlBase + "/api/allEmployees", "table_emps");
        buttonEmployee.setAttribute("disabled", true);
        buttonTask.removeAttribute("disabled");
        reverseVisibleBtnAdd(btnAddEmp, btnAddTask);
        // document.body.style.overflow = 'hidden';
    });

    btnAdd.forEach((item) => {
        item.addEventListener('click', (e) => {
            showModalForm(e);
        });
    });

    function showModalForm(e, ...p) {

        const target = e.target;
        let modalTitle;
        let list = {
            create: 'Создание',
            edit: 'Редактирование'
        };
        const formEmp = document.forms.emp;
        const formTask = document.forms.task;
        formEmp.reset();
        formTask.reset();
        if (target && target.classList.contains("btn_add_emp") || p[0] instanceof Employee) {
            modalTitle = modalEmp.querySelector(".modal_emp_title");
            selector(".modal_emp");

            if (p.length !== 0) {
                modalTitle.textContent = `${list.edit} - Сотрудника #${p[0].id}`;
                formEmp.elements.full_name.value = p[0].fullName;
                if(p[0].leader !== null)  document.querySelector(`.modal_emp select option[value='${p[0].leader}']`).selected = true;
                formEmp.elements.branch.value = p[0].branchName;
            } else {
                modalTitle.textContent = `${list.create} - Сотрудника`;
            }
            modalEmp.style.display = 'block';

        } else if (target && target.classList.contains("btn_add_task") || p[0] instanceof Task) {
            selector(".modal_task");
            modalTitle = modalTask.querySelector(".modal_task_title");
            if (p.length !== 0) {
                modalTitle.textContent = `${list.edit} - Задачи #${p[0].id}`;
                formTask.elements.description.value = p[0].description;
                if(p[0].employeeId !== null)   document.querySelector(`.modal_task select option[value='${p[0].employeeId}']`).selected = true;
                formTask.elements.priority.value = p[0].priority;
            } else {
                modalTitle.textContent = `${list.create} - Задачи`;
            }
            modalTask.style.display = 'block';

        }
    }

    function selector(nameModal) {

        let leaderSelect = document.querySelector(nameModal + " .leader_select");
        leaderSelect.innerHTML = "";
        leaderSelect.insertAdjacentHTML('beforeend', `<option value='0'></option>`);
        arrEmployees.forEach(item => {

            leaderSelect.insertAdjacentHTML('beforeend', `<option value='${item.id}'>${item.fullName}</option>`);
        });
        //     });
        // });
    }

    function reverseVisibleBtnAdd(btnVisible, btnInvisible) {
        btnInvisible.classList.add('hide');
        btnInvisible.classList.remove('show');
        btnVisible.classList.add('show');
        btnVisible.classList.remove('hide');

    }

    function startVisibleBtnAdd() {
        btnAddEmp.classList.add('show');
    }

    //Сортировка
    function clickableSort(buttonTable, buttonSort) {
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
                                // let xhttp = new XMLHttpRequest();
                                // xhttp.open("GET", urlBase + "/api/" + "get_sort_up", true);
                                // xhttp.send();
                                // xhttp.onload = function() { // (3)
                                //
                                //     if (xhttp.status != 200) {
                                //         alert(xhttp.status + ': ' + xhttp.statusText);
                                //     } else {
                                //         console.log(xhttp.response);
                                //     }
                                // }
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
                if (item.leader == null) {
                    item.leader = "";
                } else {
                    data.forEach(itemData => {
                        if (itemData.id === item.leader) {
                            item.leader = itemData.fullName;
                        }
                    });
                }
                table +=
                    `<tr class='table_tr'>
                    <td class='table_th'>${item.id}</td>
                    <td class='table_th'><a href='${item.id}' onclick='return false'>${item.fullName}</a></td>
                    <td class='table_th'>${item.leader}</td>
                    <td class='table_th'>${item.branchName}</td>
                    <td class='table_th'>${item.numberTasks}</td>
                    </tr>`;
            } else if (tableName === "table_tasks") {

                arrEmployees.forEach(itemData => {
                    if (itemData.id === item.employeeId) {
                        item.employeeId = itemData.fullName;
                    }
                });

                visible(false);
                table +=
                    `<tr class='table_tr'> 
                    <td class='table_th'>${item.id}</td> 
                    <td class='table_th'><a href='${item.id}' onclick='return false'>${item.description}</a></td>
                    <td class='table_th'>${item.employeeId}</td>
                    <td class='table_th'>${item.priority}</td>
                    </tr>`;
            }
        });
        document.querySelector("." + tableName).insertAdjacentHTML("beforeEnd", table);
        tableView();
    }

    function loadAndCreateTable(url, tableName) {
        $(document).ready(function () {
            $.get(url, function (data) {
                // console.log(data);
                if (tableName.indexOf('emps') > 0) {
                    // arrEmployees = JSON.parse(JSON.stringify(data));
                    arrEmployees = [];
                    data.forEach(item => {

                        arrEmployees.push(new Employee(item.id, item.fullName, item.leader, item.branchName, item.numberTasks));
                    });
                    console.log(arrEmployees);
                    createTable(arrEmployees, tableName);
                } else if (tableName.indexOf('tasks') > 0) {
                    arrTasks = [];
                    data.forEach(item => {
                        arrTasks.push(new Task(item.id, item.description, item.employeeId, item.priority));
                    });
                    createTable(arrTasks, tableName);
                }

            });
        });
    }

    function visible(bool) {
        let arr = ["show", "hide"];
        tableEmp.classList.add(arr[+!bool]);
        tableTask.classList.remove(arr[+!bool]);
        tableEmp.classList.remove(arr[+bool]);
        tableTask.classList.add(arr[+bool]);
    }


    class Task {
        constructor(id, description, employeeId, priority) {
            this.id = id;
            this.description = description;
            this.employeeId = employeeId;
            this.priority = priority;
        }

        getString() {
            return `Task {${this.id}, ${this.description}, ${this.employeeId}, ${this.priority}}`;
        }
    }

    class Employee {
        constructor(id, fullName, leader, branchName, numberTasks) {
            this.id = id;
            this.fullName = fullName;
            this.leader = leader;
            this.branchName = branchName;
            this.numberTasks = numberTasks;
        }

        getString() {
            return `Employee {${this.id}, ${this.fullName}, ${this.leader}, ${this.branchName}, ${this.numberTasks}}`;
        }
    }


});


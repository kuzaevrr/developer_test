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


    // form.forEach((item) => {
    //     item.addEventListener('submit', (e) => {
    //         e.preventDefault();
    //     });
    // });

    let arrEmployees = [],
        arrTasks = [],
        page = 0;


    //load table
    loadAndCreateTable(urlBase + "/api/searchEmp", {pageNumb: page}, "table_emps");

    clickableSort(table,
        document.querySelectorAll(".image_sort"));

    buttonEmployee.setAttribute("disabled", true);
    startVisibleBtnAdd();

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
                            object.numberTasks,
                            object.leaderName);
                    } else if (item.contains(tab_task)) {
                        resultObj = new Task(
                            object.id,
                            object.description,
                            object.employeeId,
                            object.priority,
                            object.employeeFullName);
                    }
                    showModalForm(item, resultObj);
                } else {

                }
            });
            // console.log(href); // может быть подгрузка с сервера, генерация интерфейса и т.п.
            return false;
        };
    });


    //Редактирование задачи

    btnFormClose.forEach((item) => {
        item.addEventListener("click", closeModal);
    });


    buttonTask.addEventListener("click", () => {
        page = 0;
        loadAndCreateTable(urlBase + "/api/searchTask", {pageNumb: page}, "table_tasks");
        buttonTask.setAttribute("disabled", true);
        buttonEmployee.removeAttribute("disabled");
        reverseVisibleBtnAdd(btnAddTask, btnAddEmp);
        // document.body.style.overflow = 'hidden';
    });

    buttonEmployee.addEventListener("click", () => {
        page = 0;
        loadAndCreateTable(urlBase + "/api/searchEmp", {pageNumb: page}, "table_emps");
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

    let pageUl = document.querySelector('.pagination ul');

    function closeModal() {
        modalEmp.style.display = 'none';
        modalTask.style.display = 'none';
        document.body.style.overflow = '';
    }

    const forms = document.querySelectorAll('form');

    forms.forEach(item => {
        clickListenerForm(item);
    });

    function clickListenerForm(item) {
        item.addEventListener('reset', (e) => {
            e.preventDefault();
            let id = item.querySelector(".modal_title").textContent.split('№')[1];
            if (document.forms.task === item) {
                deleteObj(id, new Task())
            } else if (document.forms.emp === item) {
                deleteObj(id, new Employee())
            }
            closeModal();
        });
        item.addEventListener('submit', (e) => {
            e.preventDefault();
            let id = item.querySelector(".modal_title").textContent.split('№')[1];
            if (id === undefined) {
                id = 0;
            }
            if (document.forms.task === item) {
                let select = document.querySelector(".modal_task select");
                let obj = new Task(
                    id,
                    item.elements.description.value,
                    select.options[select.selectedIndex].value,
                    item.elements.priority.value
                );
                loadSave(obj);

            } else if (document.forms.emp === item) {
                let select = document.querySelector(".modal_emp select"); // Выбираем  select по id
                let obj = new Employee(
                    id,
                    item.elements.full_name.value,
                    select.options[select.selectedIndex].value,
                    item.elements.branch.value
                );
                loadSave(obj);
            }
            closeModal();
        });
    }

    function deleteObj(id, obj) {
        const request = new XMLHttpRequest;
        let urlApi, tableName;
        if (obj instanceof Employee) {
            urlApi = '/api/searchEmp';
            tableName = 'table_emps';
            request.open('POST', urlBase + '/api/deleteEmp');
        } else if (obj instanceof Task) {
            urlApi = '/api/searchTask';
            tableName = 'table_tasks';
            request.open('POST', urlBase + '/api/deleteTask');
        }
        request.setRequestHeader('Content-type', 'application/json');
        const json = JSON.stringify(id);
        request.send(json);
        request.addEventListener('load', () => {
            if (request.status === 200) {
                // console.log('Успешно');
                loadAndCreateTable(urlBase + urlApi, {pageNumb: page}, tableName);
            } else {
                // console.log('Error');
                alert(JSON.parse(request.response).info);
            }
        });
    }


    function showModalForm(e, ...p) {

        const target = e.target;
        let modalTitle;


        const formEmp = document.forms.emp;
        const formTask = document.forms.task;
        // formEmp.reset();
        // formTask.reset();

        if (target && target.classList.contains("btn_add_emp") || p[0] instanceof Employee) {
            modalTitle = modalEmp.querySelector(".modal_emp_title");
            selector(".modal_emp", p, modalTitle, formEmp);
        } else if (target && target.classList.contains("btn_add_task") || p[0] instanceof Task) {
            modalTitle = modalTask.querySelector(".modal_task_title");
            selector(".modal_task", p, modalTitle, formTask);
        }
    }

    // function saveEmpAndTask(form, typeObj, ...id) {
    //     if (id.length === 0) {
    //         id[0] = 0;
    //     }
    // }

    function loadSave(obj) {
        const request = new XMLHttpRequest;
        let urlApi, tableName;
        if (obj instanceof Employee) {
            urlApi = '/api/searchEmp';
            tableName = 'table_emps';
            request.open('POST', urlBase + '/api/saveEmp');
        } else if (obj instanceof Task) {
            urlApi = '/api/searchTask';
            tableName = 'table_tasks';
            request.open('POST', urlBase + '/api/saveTask');
        }
        request.setRequestHeader('Content-type', 'application/json');
        const json = JSON.stringify(obj);
        request.send(json);
        request.addEventListener('load', () => {
            if (request.status === 200) {
                // console.log('Успешно');
                loadAndCreateTable(urlBase + urlApi, {pageNumb: page}, tableName);
            } else {
                alert(JSON.parse(request.response).info);
            }
        });
    }

    function selector(nameModal, p, modalTitle, form) {

        let leaderSelect = document.querySelector(nameModal + " .leader_select");
        leaderSelect.innerHTML = "";
        if (nameModal !== ".modal_task") {
            leaderSelect.insertAdjacentHTML('beforeend', `<option value='0'></option>`);
        }
        const request = new XMLHttpRequest;
        request.open('GET', urlBase + '/api/allEmployees');
        request.setRequestHeader('Content-type', 'application/json');
        request.send();
        request.addEventListener('load', () => {
            if (request.status === 200) {
                JSON.parse(request.response).forEach(item => {
                    leaderSelect.insertAdjacentHTML('beforeend', `<option value='${item.id}'>${item.fullName}</option>`);
                });
                showModalFormContinue(nameModal, p, modalTitle, form);
            } else {

            }
        });
    }

    function showModalFormContinue(nameModal, p, modalTitle, form) {
        let list = {
            create: 'Создание',
            edit: 'Редактирование'
        };
        if (nameModal === ".modal_emp") {
            if (p.length !== 0) {
                modalTitle.textContent = `${list.edit} - Сотрудника №${p[0].id}`;
                form.elements.full_name.value = p[0].fullName;
                if (p[0].leader !== null) document.querySelector(`.modal_emp select option[value="${p[0].leader}"]`).selected = true;
                form.elements.branch.value = p[0].branchName;
            } else {
                modalTitle.textContent = `${list.create} - Сотрудника`;
            }
            modalEmp.style.display = 'block';
        } else if (nameModal === ".modal_task") {
            if (p.length !== 0) {
                modalTitle.textContent = `${list.edit} - Задачи №${p[0].id}`;
                form.elements.description.value = p[0].description;
                if (p[0].employeeId !== null) document.querySelector(`.modal_task select  option[value="${p[0].employeeId}"]`).selected = true;
                form.elements.priority.value = p[0].priority;
            } else {
                modalTitle.textContent = `${list.create} - Задачи`;
            }
            modalTask.style.display = 'block';
        }
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

    /**
     * Метод сортировки сотрудников и задач
     * @param buttonTable кнопки
     * @param buttonSort
     */
    function clickableSort(buttonTable, buttonSort) {
        buttonTable.forEach(item => {
            let tableName, apiTable;
            let columns = [
                1, 1, 1, 1, 1, 1, 1, 1, 1
            ];
            item.addEventListener("click", (event) => {
                const target = event.target;
                if (target && target.classList.contains("image_sort")) {
                    buttonSort.forEach((item, i) => {
                        if (target === item) {
                            if (i >= 0 && i < 5) {
                                tableName = 'table_emps';
                                apiTable = '/api/searchEmp';
                            } else if (i >= 5 && i < 9) {
                                tableName = 'table_tasks';
                                apiTable = '/api/searchTask';
                            }
                            columns[i]++;
                            if (columns[i] > 3) {
                                columns[i] = 1;
                            }
                            if (columns[i] === 1) {
                                item.src = 'images/sort.png';
                                loadAndCreateTable(urlBase + apiTable, {pageNumb: page}, tableName);
                            } else if (columns[i] === 2) {
                                item.src = 'images/up.png';
                                buttonSort.forEach((item, x) => {
                                    if (x !== i) {
                                        columns[x] = 1;
                                        item.src = 'images/sort.png';
                                    }
                                });
                                apiSort(columns, tableName);
                            } else if (columns[i] === 3) {
                                item.src = 'images/down.png';
                                apiSort(columns, tableName);
                            }
                        }
                    });
                }
            });
        });
    }


    // обращение к серверу для сортировки
    function apiSort(columns, tableName) {

        let empOfTaskP = 0;
        if (tableName === "table_emps") empOfTaskP = 1;
        let columnsObj = {
            empId: columns[0],
            empFullName: columns[1],
            empLeader: columns[2],
            empBranch: columns[3],
            empСountTasks: columns[4],
            taskId: columns[5],
            taskDescription: columns[6],
            taskEmpId: columns[7],
            taskPriority: columns[8],
            empOfTask: empOfTaskP,
            pageNumb: page
        };

        const request = new XMLHttpRequest;
        if(empOfTaskP) request.open('POST', urlBase + '/api/getSortEmp');
        else request.open('POST', urlBase + '/api/getSortTask');
        request.setRequestHeader('Content-type', 'application/json');
        const json = JSON.stringify(columnsObj);

        request.send(json);

        request.addEventListener('load', () => {
                if (request.status === 200) {
                    // let object = JSON.parse(request.response);

                    if (tableName === "table_emps") {
                        arrEmployees = JSON.parse(JSON.stringify(JSON.parse(request.response).content)); //глубокое клонирование объекта
                        arrEmployees = [];
                        JSON.parse(request.response).content.forEach(item => {
                            arrEmployees.push(new Employee(item.id, item.fullName, item.leader, item.branchName, item.numberTasks, item.leaderName));
                        });
                        createTable(arrEmployees, tableName);
                    } else if (tableName === 'table_tasks') {
                        arrTasks = JSON.parse(JSON.stringify(JSON.parse(request.response).content));
                        arrTasks = [];
                        JSON.parse(request.response).content.forEach(item => {
                            arrTasks.push(new Task(item.id, item.description, item.employeeId, item.priority, item.employeeFullName));
                        });
                        createTable(arrTasks, tableName);


                    }
                    paginationView(JSON.parse(request.response).totalPages);
                    pagination(JSON.parse(request.response).pageable.pageNumber);
                } else {
                    // console.log('Error');
                    alert(JSON.parse(request.response).info);
                }
            }
        );
    }


    function tableView() {
        const table_body = document.querySelectorAll('.table_tr');

        table_body.forEach((item, i) => {
            if (i % 2) item.classList.add("even"); //четные
            else item.classList.add("odd");  //нечетные
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
                if (item.leaderName == null || item.leaderName === 0) {
                    item.leaderName = "";
                } else {
                    // data.forEach(itemData => {
                    //     if (itemData.id === item.leader) {
                    //         item.leader = itemData.fullName;
                    //     }
                    // });
                }
                table +=
                    `<tr class='table_tr'>
                    <td class='table_th'>${item.id}</td>
                    <td class='table_th'><a href='${item.id}' onclick='return false'>${item.fullName}</a></td>
                    <td class='table_th'>${item.leaderName}</td>
                    <td class='table_th'>${item.branchName}</td>
                    <td class='table_th'>${item.numberTasks}</td>
                    </tr>`;
            } else if (tableName === "table_tasks") {
                // arrEmployees.forEach(itemData => {
                //     if (itemData.id === item.employeeId) {
                //         item.employeeId = itemData.fullName;
                //     }
                // });
                visible(false);
                table +=
                    `<tr class='table_tr'> 
                    <td class='table_th'>${item.id}</td> 
                    <td class='table_th'><a href='${item.id}' onclick='return false'>${item.description}</a></td>
                    <td class='table_th'>${item.employeeFullName}</td>
                    <td class='table_th'>${item.priority}</td>
                    </tr>`;
            }
        });


        for (let y = 0; y < (20 - data.length); y++) {
            let xx;
            let str = '';
            if (tableName === "table_tasks") {
                xx = 4
            } else if (tableName === "table_emps") {
                xx = 5
            }
            for (let x = 0; x < xx; x++) {
                str += "<td class='table_th'></td> ";
            }
            table +=
                `<tr class='table_tr'>
                    ${str}
                    </tr>`;
        }


        document.querySelector("." + tableName).insertAdjacentHTML("beforeEnd", table);
        tableView();
    }


    function loadAndCreateTable(url, page, tableName) {

        const request = new XMLHttpRequest;
        request.open('POST', url);
        request.setRequestHeader('Content-type', 'application/json');
        request.send(JSON.stringify(page));
        request.addEventListener('load', () => {
            if (request.status === 200) {
                let object = JSON.parse(request.response);

                if (tableName.indexOf('emps') > 0) {
                    arrEmployees = JSON.parse(JSON.stringify(object.content)); //глубокое клонирование объекта
                    arrEmployees = [];
                    object.content.forEach(item => {
                        arrEmployees.push(new Employee(item.id, item.fullName, item.leader, item.branchName, item.numberTasks, item.leaderName));
                    });
                    createTable(arrEmployees, tableName);
                } else if (tableName.indexOf('tasks') > 0) {
                    arrTasks = [];
                    object.content.forEach(item => {
                        arrTasks.push(new Task(item.id, item.description, item.employeeId, item.priority, item.employeeFullName));
                    });
                    createTable(arrTasks, tableName);

                }
                paginationView(object.totalPages);
                pagination(object.pageable.pageNumber);
            } else {
                alert(JSON.parse(request.response));
            }
        });
    }

    function visible(bool) {
        let arr = ["show", "hide"];
        tableEmp.classList.add(arr[+!bool]);
        tableTask.classList.remove(arr[+!bool]);
        tableEmp.classList.remove(arr[+bool]);
        tableTask.classList.add(arr[+bool]);
    }

    function paginationView(countPage) {
        let pageUl = document.querySelector('.pagination ul');
        pageUl.innerHTML = '';
        let htmlDomLi = '';
        htmlDomLi += '<li><a><</a></li>';
        for (let i = 0; i < countPage; i++) {
            htmlDomLi += `<li><a>${i + 1}</a></li>`;
        }
        htmlDomLi += '<li><a>></a></li>';
        pageUl.insertAdjacentHTML("beforeEnd", htmlDomLi);

    }

    function pagination(pageNumber) {
        document.querySelectorAll('.pagination li').forEach((item, index) => {
            let paginationDom = document.querySelectorAll('.pagination li');
            paginationDom.item(pageNumber + 1).style.fontWeight = "900";
            item.addEventListener('click', (e) => {

                if (index === 0 && page !== 0) {
                    page = page - 1;
                } else if (index === paginationDom.length - 1 && page !== paginationDom.length - 3) {
                    console.log(page);
                    console.log(paginationDom.length - 3);
                    page = page + 1;
                } else if (index !== 0 && index !== paginationDom.length - 1) {
                    page = index - 1;
                }
                //Сделать валидацию)
                if (tableEmp.classList.contains("show")) {
                    loadAndCreateTable(urlBase + "/api/searchEmp", {pageNumb: page}, "table_emps");
                } else if (tableTask.classList.contains("show")) {
                    loadAndCreateTable(urlBase + "/api/searchTask", {pageNumb: page}, "table_tasks");
                }


            });
        });
    }


    class Task {
        constructor(id, description, employeeId, priority, employeeFullName) {
            this.id = id;
            this.description = description;
            this.employeeId = employeeId;
            this.priority = priority;
            this.employeeFullName = employeeFullName;
        }

        getString() {
            return `Task {${this.id}, ${this.description}, ${this.employeeId}, ${this.priority}}`;
        }
    }

    class Employee {
        constructor(id, fullName, leader, branchName, numberTasks, leaderName) {
            this.id = id;
            this.fullName = fullName;
            this.leader = leader;
            this.branchName = branchName;
            this.numberTasks = numberTasks;
            this.leaderName = leaderName;
        }

        getString() {
            return `Employee {${this.id}, ${this.fullName}, ${this.leader}, ${this.branchName}, ${this.numberTasks}}`;
        }
    }
})
;
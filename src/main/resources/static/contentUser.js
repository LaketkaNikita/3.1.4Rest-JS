fetch("api/user")
    .then(response => {
            response.json().then(
                data => {
                    let tableBody = "";
                    tableBody += "<tr>" + "<td>" + data.id + "</td>";
                    tableBody += "<td>" + data.name + "</td>";
                    tableBody += "<td>" + data.lastname + "</td>";
                    tableBody += "<td>" + data.age + "</td>";
                    tableBody += "<td>" + data.email + "</td>";
                    tableBody += "<td>";
                    let rolesName = "";
                    data.roles.forEach(r => {
                        rolesName += r.role + " ";
                    })
                    tableBody += rolesName + "</td>" + "</tr>";
                    document.getElementById("tableUserBody").innerHTML = tableBody;
                }
            )
        }
    )

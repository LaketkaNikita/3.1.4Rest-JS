fetch("http://localhost:8080/api/users")
    .then(response => {
            response.json().then(
                data => {
                    console.log(data);
                    if (data.length > 0) {
                        let temp = "";
                        data.forEach(u => {
                                temp += "<tr id=\"" + u.id + "\">";
                                temp += "<td>" + u.id + "</td>";
                                temp += "<td>" + u.name + "</td>";
                                temp += "<td>" + u.lastname + "</td>";
                                temp += "<td>" + u.age + "</td>";
                                temp += "<td>" + u.email + "</td>";
                                temp += "<td>";
                                let rolesName = "";
                                u.roles.forEach(r => {
                                    rolesName += r.role + " ";
                                })
                                temp += rolesName + "</td>" + "</tr>";
                            }
                        )
                        document.getElementById("userTableBody").innerHTML =temp;
                    }}
            )
        }
    )
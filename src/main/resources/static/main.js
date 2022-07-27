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
                            temp += rolesName + "</td>";
                            temp += "<td><button class=\"btn btn-info\" onclick=\"pressEdit(this)\" id=\"editBtn" + u.id + "\">Edit</button></td>";
                            temp += "<td><button class=\"btn btn-danger\" onclick=\"pressDel(this)\" id=\"deleteBtn" + u.id + "\">Delete</button></td>" + "</tr>";
                        }
                    )
                    document.getElementById("EditTable").innerHTML =temp;
                }}
            )
            }
        )
fetch("/api/user")
    .then(response => {
            response.json().then(
                data => {
                    console.log(data);
                    if (data.length > 0) {
                        let temp = "";
                        let rolesName = "";
                        data.roles.forEach(r => {
                            rolesName += r.role + " ";
                        })
                        temp = data.email + " with roles " + rolesName;
                        document.getElementById("titleString").innerHTML = temp;
                    }
                }
            )
        }
    )
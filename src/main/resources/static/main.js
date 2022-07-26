const requestURL = 'http://localhost:8080/api/users'

const  xhr = new XMLHttpRequest()

xhr.open('GET', requestURL)

xhr.onload = () => {
    console.log(xhr.response)
}

xhr.send()
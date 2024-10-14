
if (document.getElementById("form") !== null)
    document.getElementById("form").onsubmit =
        function() {
            let formData = {"text": document.forms["form"].elements[0].value};

            fetch(window.location.href.replace("index.html", "") + "api", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            }).then(res => res.text()).then((text) => {
                    document.getElementById("result").innerHTML = text;
                });
            return false;
        }

if (document.getElementById("formH") !== null)
    document.getElementById("formH").onsubmit =
        function() {
            let numVertices = document.forms["formH"].elements[0].value;
            let numEdges = document.forms["formH"].elements[1].value;

            let formData = {
                "numVertices": parseInt(numVertices),
                "numEdges": parseInt(numEdges)
            };

            fetch(window.location.href.replace("indexH.html", "") + "home", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            }).then(res => res.text()).then((text) => {
                document.getElementById("result").innerHTML = text;
            });
            return false;
        }

if (document.getElementById("formB") !== null)
    document.getElementById("formB").onsubmit =
        function() {
            let numVertices = document.forms["formB"].elements[0].value;
            let numEdges = document.forms["formB"].elements[1].value;

            let formData = {
                "numVertices": parseInt(numVertices),
            };

            fetch(window.location.href.replace("indexB.html", "") + "bonus", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            }).then(res => res.text()).then((text) => {
                document.getElementById("result").innerHTML = text;
            });
            return false;
        }
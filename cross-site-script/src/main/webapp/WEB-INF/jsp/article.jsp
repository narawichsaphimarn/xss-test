<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Articles</title>
    <script>
        async function loadArticles() {
            const token = localStorage.getItem("token");
            if (!token) {
                window.location.href = "login.jsp";
                return;
            }

            const response = await fetch("/articles/all", {
                method: "GET",
                headers: { "Authorization": "Bearer " + token }
            });

            if (response.ok) {
                const articles = await response.json();
                document.getElementById("articles").innerText = JSON.stringify(articles, null, 2);
            } else {
                alert("Unauthorized or session expired");
                localStorage.removeItem("token");
                window.location.href = "login.jsp";
            }
        }

        function logout() {
            localStorage.removeItem("token");
            window.location.href = "login.jsp";
        }

        window.onload = loadArticles;
    </script>
</head>
<body>
    <h2>Articles</h2>
    <button onclick="logout()">Logout</button>
    <pre id="articles"></pre>
</body>
</html>
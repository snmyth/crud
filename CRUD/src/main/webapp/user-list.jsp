<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.entity.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student List</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Montserrat&display=swap');
        @import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css');

        body {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            width: 100vw;
            min-height: 100vh;
            margin: 0;
            background-color: #9ca3af;
            font-family: 'Montserrat', sans-serif;
        }
        .card {
            width: 350px;
            background-color: #f9f9f9;
            border-radius: 8px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            padding: 30px;
            margin: 20px;
            text-align: center;
        }
        .profile-img {
            width: 80px;
            height: 80px;
            border-radius: 50%;
            object-fit: cover;
            margin-bottom: 20px;
        }
        .social-btn {
            width: 48px;
            height: 48px;
            border-radius: 50%;
            background-color: #f9f9f9;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 5px;
        }
        .primary-btn {
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 10px 15px;
            border-radius: 4px;
            background-color: #1eb853;
            color: white;
            margin: 10px 0;
            border: none;
            cursor: pointer;
        }
        .primary-btn:hover {
            background-color: #16a842;
        }
        .card-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            margin: 20px;
        }
    </style>
</head>
<body>
    <div class="card-container">
        <%
        List<User> users = (List<User>) request.getAttribute("ListUser");
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
        %>
            <div class="card">
                <img src="https://tools-api.webcrumbs.org/image-placeholder/80/80/student%20photo/<%= user.getId() %>" alt="Student Photo" class="profile-img"/>
                <h2 class="text-xl font-bold"><%= user.getName() %></h2>
                <div>
                    <span class="font-bold">Level:</span> <span><%= user.getLevel() %></span>
                </div>
                <div>
                    <span class="font-bold">Type:</span> <span><%= user.isTeacher() ? "Teacher" : "Student" %></span>
                </div>

                <div style="margin-top: 20px;">
                    <a href="#" class="social-btn" aria-label="Facebook">
                        <i class="fa-brands fa-facebook text-xl"></i>
                    </a>
                    <a href="#" class="social-btn" aria-label="Instagram">
                        <i class="fa-brands fa-instagram text-xl"></i>
                    </a>
                    <a href="#" class="social-btn" aria-label="Email">
                        <span class="material-symbols-outlined text-xl">mail</span>
                    </a>
                </div>

                <div>
                    <button class="primary-btn">
                        <span class="material-symbols-outlined">edit</span>
                        <span>Edit</span>
                    </button>
                    <button class="primary-btn">
                        <span class="material-symbols-outlined">delete</span>
                        <span>Delete</span>
                    </button>
                </div>
            </div>
        <%
            }
        } else {
        %>
            <div class="card">
                <h2>No students found.</h2>
            </div>
        <%
        }
        %>
    </div>
</body>
</html>

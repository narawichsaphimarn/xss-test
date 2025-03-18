"use client";

import Link from "next/link";
import { FormEvent } from "react";

export default function Login() {
  const login = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      const formData = new FormData(event.currentTarget);
      const data = {
        username: formData.get("username"),
        password: formData.get("password"),
      };

      const response = await fetch("http://localhost:8080/v1/api/auth/login", {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (!response.ok) {
        throw new Error(`HTTP Error! Status: ${response.status}`);
      }

      const resp = await response.json();
      if (resp["token"] !== null && resp["username"] !== null) {
        localStorage.setItem("token_access", resp["token"]);
        localStorage.setItem("user", resp["username"]);
        window.location.href = "/";
      }
    } catch (error) {
      alert(error);
    }
  };
  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <h1 className="text-2xl mb-4">Login</h1>
      <form
        onSubmit={(e) => login(e)}
        className="bg-white p-6 rounded shadow-md w-80 text-black"
      >
        <input
          id="username"
          name="username"
          type="username"
          placeholder="Username"
          className="w-full p-2 mb-2 border rounded"
        />
        <input
          id="password"
          name="password"
          type="password"
          placeholder="Password"
          className="w-full p-2 mb-4 border rounded"
        />
        <button
          type="submit"
          className="w-full bg-blue-600 text-white p-2 rounded"
        >
          Login
        </button>
      </form>
      <Link href="/register" className="text-blue-600 mt-2">
        Register
      </Link>
    </div>
  );
}

'use client'

import Link from "next/link";
import { useRouter } from "next/navigation";
import { FormEvent } from "react";

export default function Register() {
  const router = useRouter();

  const create = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const formData = new FormData(e.currentTarget);
      if (formData.get("password") !== formData.get("confirmPassword")) {
        throw new Error(`Invalid password mistmach`);
      }
      const data = {
        username: formData.get("username"),
        email: formData.get("email"),
        password: formData.get("password"),
      };
      const response = await fetch("http://localhost:8080/v1/api/users", {
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
      if (resp["id"] !== null) {
        router.push("/login");
      }
    } catch (error) {
      alert(error);
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen">
      <h1 className="text-2xl mb-4">Register</h1>
      <form
        onSubmit={(e) => create(e)}
        className="bg-white p-6 rounded shadow-md w-80 text-black"
      >
        <input
          id="username"
          name="username"
          type="text"
          placeholder="Username"
          className="w-full p-2 mb-2 border rounded"
        />
        <input
          id="email"
          name="email"
          type="email"
          placeholder="Email"
          className="w-full p-2 mb-2 border rounded"
        />
        <input
          id="password"
          name="password"
          type="password"
          placeholder="Password"
          className="w-full p-2 mb-2 border rounded"
        />
        <input
          id="confirmPassword"
          name="confirmPassword"
          type="password"
          placeholder="Confirm Password"
          className="w-full p-2 mb-4 border rounded"
        />
        <button
          type="submit"
          className="w-full bg-blue-600 text-white p-2 rounded"
        >
          Register
        </button>
      </form>
      <Link href="/login" className="text-blue-600 mt-2">
        Login
      </Link>
    </div>
  );
}

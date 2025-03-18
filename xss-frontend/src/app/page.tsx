"use client";
import Link from "next/link";
import { FormEvent, useEffect, useState } from "react";

type content = {
  id: number;
  title: string;
  content: string;
};

export default function Home() {
  const [user, setUser] = useState<string | null>(null);
  const [isOpen, setIsOpen] = useState(false);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [articles, setArticles] = useState<content[]>([]);

  useEffect(() => {
    

    setUser(localStorage.getItem("user"));
    getArticle();
  }, []);

  const getArticle = async () => {
      return await fetch("http://localhost:8080/v1/api/contents")
        .then((response) => response.json())
        .then((data) => setArticles(data));
    };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const newArticle = {
      title,
      content,
    };

    const response = await fetch("http://localhost:8080/v1/api/contents", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token_access")}`,
      },
      body: JSON.stringify(newArticle),
    });

    if (response.ok) {
      toggleModal();
      getArticle();
    } else {
      alert("Failed to create article");
    }
  };

  const toggleModal = () => {
    setIsOpen(!isOpen);
    setTitle("");
    setContent("");
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl mb-4">Home</h1>
      {user !== null ? (
        <button
          onClick={toggleModal}
          className="mb-4 p-2 bg-green-600 text-white rounded"
        >
          Add Article
        </button>
      ) : (
        <></>
      )}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {articles.map((article) => (
          <div key={article.id} className="p-4 bg-white shadow-md rounded">
            <h2 className="text-xl font-bold text-black">{article.title}</h2>
            {/* <div
              className="text-gray-600"
              dangerouslySetInnerHTML={{
                __html: article.content,
              }}
            /> */}
            <Link href={`/article/${article.id}`} className="text-blue-600">
              Read More
            </Link>
          </div>
        ))}
      </div>

      {/* Modal */}
      {isOpen && (
        <div className="fixed inset-0 flex items-center justify-center bg-gray-900 bg-opacity-50">
          <div className="bg-white p-6 rounded shadow-lg w-96 text-black">
            <h2 className="text-xl font-bold mb-4">Create Article</h2>
            <form onSubmit={handleSubmit}>
              <input
                type="text"
                name="title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="Title"
                className="w-full p-2 mb-2 border rounded"
              />
              <textarea
                name="content"
                value={content}
                onChange={(e) => setContent(e.target.value)}
                placeholder="HTML Content"
                className="w-full p-2 mb-4 border rounded h-32"
              />
              <div className="flex justify-end space-x-2">
                <button
                  type="button"
                  onClick={toggleModal}
                  className="p-2 bg-gray-500 text-white rounded"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="p-2 bg-blue-600 text-white rounded"
                >
                  Create
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

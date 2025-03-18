import Link from "next/link";

export default function Navbar({ user }: { user: string | null }) {
  const signout = () => {
    localStorage.clear();
    window.location.href = "/";
  };

  return (
    <nav className="p-4 bg-blue-600 text-white flex justify-between items-center">
      <Link href="/" className="text-xl font-bold">
        MyBlog
      </Link>
      <div className="flex">
        {user ? (
          <>
            <span className="mr-4">Welcome, {user}</span>
            <button
              onClick={() => signout()}
              className="w-full bg-red-600 text-white p-2 rounded"
            >
              Sign out
            </button>
          </>
        ) : (
          <>
            <Link href="/login" className="mr-4">
              Login
            </Link>
            <Link href="/register">Register</Link>
          </>
        )}
      </div>
    </nav>
  );
}

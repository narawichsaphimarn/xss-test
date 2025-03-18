import { notFound } from "next/navigation";

type Content = {
  id: number;
  title: string;
  content: string;
  user: string;
};

async function getContent(id: string) {
  const res = await fetch(`http://localhost:8080/v1/api/contents/${id}`, {
    cache: "force-cache",
  });
  if (!res.ok) {
    notFound();
  }
  const post: Content = await res.json();
  if (!post) notFound();
  return post;
}

export default async function Article({
  params,
}: {
  params: Promise<{ slug: string }>;
}) {
  const { slug } = await params;
  const content = await getContent(slug);

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold">{content.title}</h1>
      <p className="text-gray-600">{`By ${content.user}`}</p>
      <div
        className="mt-4"
        dangerouslySetInnerHTML={{
          __html: content.content,
        }}
      />
    </div>
  );
}

import NavbarHome from "@/components/navbar_home";
import { faPaperPlane } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Poppins } from "next/font/google";
import matter from "gray-matter";
import md from "markdown-it";
var taskLists = require('markdown-it-task-lists');

const FontPoppins = Poppins({
    weight: '500',
    styles: ["regular"],
    subsets: ["latin"],
});



export default function BlogPage({ frontMatter, content }) {
    return (
        <div className="p-6 w-[100vw] flex flex-col justify-center items-center">
            <NavbarHome />

            <div className="w-3/6 flex flex-col justify-center">
                {/* Image */}
                <img className="w-full" src="https://cdn.hashnode.com/res/hashnode/image/upload/v1671775779794/f4799720-8bb6-46b0-82ba-34a6ff39f07a.png?w=1600&h=840&fit=crop&crop=entropy&auto=compress,format&format=webp" alt="Sunset in the mountains" />
                {/* Title */}
                <div className={"font-semibold text-4xl my-4 text-center "+FontPoppins.className}>The Coldest Sunset</div>
                {/* Author name and date */}
                <p className="text-black-700 text-base mb-1 text-center">
                    Written by <i>Tanmoy Sarkar</i>
                </p>
                <p className="text-gray-700 text-base mb-1 text-center">
                    <FontAwesomeIcon icon={faPaperPlane} /> Published on 12th June, 2021
                </p>

                {/* Blog */}
                <article
                    className='prose lg:prose-xl'
                    dangerouslySetInnerHTML={{ __html: md().use(taskLists).render(content) }}
                />
            </div>
        </div>
    );
}

export async function getServerSideProps({ params: { id } }) {
    // get content for each blog
    console.log(id);
    const markdown = `
# Header 1
## Header 2

- [ ] Task list 1
- [ ] Pending task list 2
-[x] Completed task list 3
[x] Completed task list 4

**Bold text**

*Italic text*

---

> Blockquote

[Link](https://google.com)

`.toString();
    const { data: frontMatter, content } = matter(markdown);
    console.log(frontMatter);
    console.log(content);

  
    return {
      props: {
        frontMatter,
        content,
      },
    };
  }
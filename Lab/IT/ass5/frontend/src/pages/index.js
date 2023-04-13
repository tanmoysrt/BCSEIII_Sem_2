import BlogCard from "@/components/card";
import NavbarHome from "@/components/navbar_home";

export default function HomePage(){
    return (
        <div className="p-6 w-[100vw] flex flex-col justify-center">
            <NavbarHome />

            {/* Blog cards */}
            <div className="flex flex-wrap gap-12 justify-center mx-14">
                <BlogCard />
                <BlogCard />
                <BlogCard />
                <BlogCard />
                <BlogCard />
                <BlogCard />
                <BlogCard />
                <BlogCard />
            </div>
        </div>
    )
}
import styles from "../styles.css";

function Header() {
    return (
        <header class="masthead text-center text-white">
            <div class="masthead-content">
                <div class="container px-5">
                    <h1 class="masthead-heading mb-0">인덱스 헤더예용</h1>
                    <h2 class="masthead-subheading mb-0">짜증나는 리액트 ^^</h2>
                    <a class="btn btn-primary btn-xl rounded-pill mt-5" href="#scroll">가보자고....</a>
                </div>
            </div>
            <div class="bg-circle-1 bg-circle"></div>
            <div class="bg-circle-2 bg-circle"></div>
            <div class="bg-circle-3 bg-circle"></div>
            <div class="bg-circle-4 bg-circle"></div>
        </header>
    )

}
export default Header;
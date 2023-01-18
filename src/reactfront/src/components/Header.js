
function Header() {
    return (
        <header className="masthead text-center text-white">
            <div className="masthead-content">
                <div className="container px-5">
                    <h1 className="masthead-heading mb-0">Today Is Trading Day!</h1>
                    <h2 className="masthead-subheading mb-0">당신의 프로젝트 파트너를 구해보세요!</h2>
                    <a className="btn btn-primary btn-xl rounded-pill mt-5" href="/item/itemBoard">찾아보기</a>
                </div>
            </div>
            <div className="bg-circle-1 bg-circle"></div>
            <div className="bg-circle-2 bg-circle"></div>
            <div className="bg-circle-3 bg-circle"></div>
            <div className="bg-circle-4 bg-circle"></div>
        </header>
    )

}
export default Header;
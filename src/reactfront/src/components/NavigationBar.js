import {Link} from "react-router-dom";

function NavigationBar() {
    return (
        <nav className="navbar navbar-expand-lg navbar-dark navbar-custom fixed-top">

            <div className="container px-5">
                <Link to={`/`}><a className="navbar-brand" href="#page-top">Trading Day</a> </Link>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation"><span className="navbar-toggler-icon"></span></button>
                <div className="collapse navbar-collapse" id="navbarResponsive">
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item"><a className="nav-link" href="#!">Trade!</a></li>
                        <li className="nav-item"><a className="nav-link" href="#!">Community</a></li>
                        <Link to={`/qna/qnaBoard`}> <li className="nav-item"><a className="nav-link" href="">Support </a></li></Link>
                        <li className="nav-item"><a className="nav-link" href="#!">Sign Up</a></li>
                        <li className="nav-item"><a className="nav-link" href="#!">Log In</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    )
}

export default NavigationBar;
import styles from "../css/styles.css";

function Footer() {
    return (
        <div className="fixed-bottom">
            <footer className="py-5 bg-black my-auto" style={{height:"50px"}}>
                <div className="container px-5" ><p className="m-0 text-center text-white small">Copyright &copy; TradingDay</p></div>
                <div className="container px-5" ><p className="m-0 text-center text-white small">ContextUs: email.test.only134@gmail.com</p></div>
            </footer>

        </div>
    )
}

export default Footer;
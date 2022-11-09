import css from "../styles.css";
import Footer from "./Footer";
import Header from "./Header";
import NavigationBar from "./NavigationBar";


function MainLayout () {
    return (
        <div>
            <NavigationBar />
            <Footer />
        </div>
    )
}

export default MainLayout;
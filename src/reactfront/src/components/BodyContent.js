import styles from "../css/styles.css";
import img1 from "../img/01.jpg";
import img2 from "../img/02.jpg";
import img3 from "../img/03.jpg";
import favicon from "../img/favicon.ico";
function BodyContent() {
    return (
        <div>
            <section id="scroll">
                <div className="container px-5">
                    <div className="row gx-5 align-items-center">
                        <div className="col-lg-6 order-lg-2">
                            <div className="p-5"><img className="img-fluid rounded-circle" src={img1} alt="..." /></div>
                        </div>
                        <div className="col-lg-6 order-lg-1">
                            <div className="p-5">
                                <h2 className="display-4" style={{fontWeight:"bold"}}>미래의 파트너를 구해보세요!</h2>
                                <p style={{fontStyle:"oblique"}}>Trade 게시판에서 당신의 미래의 멋진 파트너를 찾아보세요! 직접 찾아볼 수 있는것은 물론, &nbsp; 찾아갈 수도 있습니다!</p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section>
                <div className="container px-5">
                    <div className="row gx-5 align-items-center">
                        <div className="col-lg-6">
                            <div className="p-5"><img className="img-fluid rounded-circle" src={img2} alt="..." /></div>
                        </div>
                        <div className="col-lg-6">
                            <div className="p-5">
                                <h2 className="display-4" style={{fontWeight:"bold"}}>제안서를 &nbsp; 구인자에게 보내보세요.</h2>
                                <p style={{fontStyle:"oblique"}}>모집 게시글에서 구인자에게 프로젝트 참가 의사를 밝혀봐요! 최고의 파트너가 될 수 있는 기회입니다!</p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section>
                <div className="container px-5">
                    <div className="row gx-5 align-items-center">
                        <div className="col-lg-6 order-lg-2">
                            <div className="p-5"><img className="img-fluid rounded-circle" src={img3} alt="..." /></div>
                        </div>
                        <div className="col-lg-6 order-lg-1">
                            <div className="p-5">
                                <h2 className="display-4" style={{fontWeight:"bold"}}>모든 파트너분들의 편의가 제일 중요합니다.</h2>
                                <p style={{fontStyle:"oblique"}}>불편한 점, 개선사항, 요구사항은 언제든 문의사항에 남겨주세요! 파트너분들의 의견 하나하나 귀담아 듣겠습니다.</p>
                            </div>
                        </div>
                    </div>
                </div>
                {/*<div className="fixed-bottom">*/}
                <div>
                    <footer className="py-5 bg-black my-auto" style={{height:"50px"}}>
                            <div className="container px-5" ><p className="m-0 text-center text-white small">Copyright &copy; TradingDay</p></div>
                            <div className="container px-5" ><p className="m-0 text-center text-white small">ContextUs: email.test.only134@gmail.com</p></div>
                    </footer>
                </div>
            </section>
        </div>
    )
}
export default BodyContent;
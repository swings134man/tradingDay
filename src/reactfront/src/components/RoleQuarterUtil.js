// 로그인한 유저의 권한체크 ==> 리턴값을 활용하여 분기
export const manageQuarter = (memberId: string) => {
    let val;
    if(memberId === "admin" || memberId === "manager") {
        val = "admin";
    } else {
        val = "user";
    }
    return val;
}
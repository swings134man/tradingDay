// 로그인한 유저의 권한체크 ==> 리턴값을 활용하여 분기
export const manageQuarter = (userRole: string) => {
    let val;
    //"ROLE_USER"
    if(userRole === "ROLE_ADMIN" || userRole === "ROLE_MANAGER") {
        val = "admin";
    } else {
        val = "user";
    }
    return val;
}
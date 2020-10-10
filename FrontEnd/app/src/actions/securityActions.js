import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newPerson, history) => async dispatch => {
    try {
        await axios.post("http://localhost:8080/api/Account", newPerson);
        history.push("/auth/login");
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    }
    catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
}

export const login = (LoginRequest, history) => async dispatch => {
    try {
        const res = await axios.post("http://localhost:8080/api/Account/Login", LoginRequest);
        const { token } = res.data;
        localStorage.setItem('jwtToken', token);
        setJWTToken(token);
        const decoded = jwt_decode(token);
        history.push("/admin/services_dashboard")
        dispatch ({
            type: SET_CURRENT_USER,
            payload: decoded
        });
    }
    catch(err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
}
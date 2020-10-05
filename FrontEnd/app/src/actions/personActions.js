import axios from "axios";
import { GET_ERRORS } from "./types";

export const createPerson = (person, history) => async dispatch => {
  try {
    const res = await axios.post("http://3.237.203.90:8080/api/Account", person);
    history.push("/services_dashboard");
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

import { GET_ERRORS } from "../actions/types";

const initialState = {};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_ERRORS:
      if(Array.isArray(action.payload)) {
        const errors = {};
        action.payload.forEach(function(error) {
          const field = error.field;
          errors[field] = error.defaultMessage;
        });
        return errors;
      } else {
        if(action.payload.field) {
          return {
            [action.payload.field]: action.payload.defaultMessage
          };
        } else {
          return {
            "email": action.payload.defaultMessage,
            "password": action.payload.defaultMessage
          };
        }
      }

    default:
      return state;
  }
}

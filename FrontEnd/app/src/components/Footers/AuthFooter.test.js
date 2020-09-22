import React from "react";
import AuthFooter from "./AuthFooter";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter() });

describe('<AuthFooter /> Component Rendered', () => {
    const mockFn = jest.fn();
    it("should render 1 <AuthFooter /> component", ()=>{
        const component = shallow(<AuthFooter/>);
        expect(component).toHaveLength(1);
    });
})
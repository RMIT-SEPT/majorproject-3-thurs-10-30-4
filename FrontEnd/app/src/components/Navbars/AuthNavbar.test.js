import React from "react";
import AuthNavbar from "./AuthNavbar";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter() });

describe('<AuthNavbar /> Component Rendered', () => {
    const mockFn = jest.fn();
    it("should render 1 <AuthNavbar /> component", ()=>{
        const component = shallow(<AuthNavbar/>);
        expect(component).toHaveLength(1);
    });
})
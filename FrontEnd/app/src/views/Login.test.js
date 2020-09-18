import React from "react";
import Login from "./Login";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter() });

describe('<Login /> Component Rendered', () => {
    const mockFn = jest.fn();
    it("should render 1 <Login /> component", ()=>{
        const component = shallow(<Login/>);
        expect(component).toHaveLength(1);
    });
})
import React from "react";
import Register from "./Register";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter() });

describe('<Register /> Component Rendered', () => {
    const mockFn = jest.fn();
    it("should render 1 <Register /> component", ()=>{
        const component = shallow(<Register/>);
        expect(component).toHaveLength(1);
    });
})
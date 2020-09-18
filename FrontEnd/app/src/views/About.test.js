import React from "react";
import About from "./About";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter() });

describe('<About /> Component Rendered', () => {
    const mockFn = jest.fn();
    it("should render 1 <About /> component", ()=>{
        const component = shallow(<About/>);
        expect(component).toHaveLength(1);
    });
})
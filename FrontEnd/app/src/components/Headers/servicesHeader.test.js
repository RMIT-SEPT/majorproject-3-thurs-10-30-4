import React from "react";
import ServicesHeader from "./ServicesHeader";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter() });

describe('<ServicesHeader /> Component Rendered', () => {
    const mockFn = jest.fn();
    it("should render 1 <ServicesHeader /> component", ()=>{
        const component = shallow(<ServicesHeader/>);
        expect(component).toHaveLength(1);
    });
})
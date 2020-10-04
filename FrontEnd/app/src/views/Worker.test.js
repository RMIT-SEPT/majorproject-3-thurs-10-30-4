import React from "react";
import Worker from "./Worker";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter() });

describe('<Worker /> Component Rendered', () => {
    const mockFn = jest.fn();
    it("should render 1 <Worker /> component", ()=>{
        const component = shallow(<Worker/>);
        expect(component).toHaveLength(1);
    });
})
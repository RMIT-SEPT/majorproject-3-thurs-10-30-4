import React from "react";
import ServicesDashboard from "./ServicesDashboard";
import {shallow, mount} from "enzyme";
import Enzyme from "enzyme";
import Adapter from "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter() });

describe('<ServicesDashboard /> Component Rendered', () => {
    const mockFn = jest.fn();
    it("should render 1 <ServicesDashboard /> component", ()=>{
        const component = shallow(<ServicesDashboard/>);
        expect(component).toHaveLength(1);
    });
})
/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Operator from './operator.vue';
import OperatorService from './operator.service';
import AlertService from '@/shared/alert/alert.service';

type OperatorComponentType = InstanceType<typeof Operator>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Operator Management Component', () => {
    let operatorServiceStub: SinonStubbedInstance<OperatorService>;
    let mountOptions: MountingOptions<OperatorComponentType>['global'];

    beforeEach(() => {
      operatorServiceStub = sinon.createStubInstance<OperatorService>(OperatorService);
      operatorServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          operatorService: () => operatorServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        operatorServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(Operator, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(operatorServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.operators[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: OperatorComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Operator, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        operatorServiceStub.retrieve.reset();
        operatorServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        operatorServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeOperator();
        await comp.$nextTick(); // clear components

        // THEN
        expect(operatorServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(operatorServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});

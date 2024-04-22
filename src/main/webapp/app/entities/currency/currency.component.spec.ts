/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Currency from './currency.vue';
import CurrencyService from './currency.service';
import AlertService from '@/shared/alert/alert.service';

type CurrencyComponentType = InstanceType<typeof Currency>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Currency Management Component', () => {
    let currencyServiceStub: SinonStubbedInstance<CurrencyService>;
    let mountOptions: MountingOptions<CurrencyComponentType>['global'];

    beforeEach(() => {
      currencyServiceStub = sinon.createStubInstance<CurrencyService>(CurrencyService);
      currencyServiceStub.retrieve.resolves({ headers: {} });

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
          currencyService: () => currencyServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        currencyServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(Currency, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(currencyServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.currencies[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: CurrencyComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Currency, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        currencyServiceStub.retrieve.reset();
        currencyServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        currencyServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeCurrency();
        await comp.$nextTick(); // clear components

        // THEN
        expect(currencyServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(currencyServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});

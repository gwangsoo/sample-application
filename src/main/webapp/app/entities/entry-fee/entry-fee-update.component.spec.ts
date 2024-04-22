/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EntryFeeUpdate from './entry-fee-update.vue';
import EntryFeeService from './entry-fee.service';
import AlertService from '@/shared/alert/alert.service';

import CurrencyService from '@/entities/currency/currency.service';

type EntryFeeUpdateComponentType = InstanceType<typeof EntryFeeUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const entryFeeSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<EntryFeeUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('EntryFee Management Update Component', () => {
    let comp: EntryFeeUpdateComponentType;
    let entryFeeServiceStub: SinonStubbedInstance<EntryFeeService>;

    beforeEach(() => {
      route = {};
      entryFeeServiceStub = sinon.createStubInstance<EntryFeeService>(EntryFeeService);
      entryFeeServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          entryFeeService: () => entryFeeServiceStub,
          currencyService: () =>
            sinon.createStubInstance<CurrencyService>(CurrencyService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(EntryFeeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.entryFee = entryFeeSample;
        entryFeeServiceStub.update.resolves(entryFeeSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(entryFeeServiceStub.update.calledWith(entryFeeSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        entryFeeServiceStub.create.resolves(entity);
        const wrapper = shallowMount(EntryFeeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.entryFee = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(entryFeeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        entryFeeServiceStub.find.resolves(entryFeeSample);
        entryFeeServiceStub.retrieve.resolves([entryFeeSample]);

        // WHEN
        route = {
          params: {
            entryFeeId: '' + entryFeeSample.id,
          },
        };
        const wrapper = shallowMount(EntryFeeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.entryFee).toMatchObject(entryFeeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        entryFeeServiceStub.find.resolves(entryFeeSample);
        const wrapper = shallowMount(EntryFeeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});

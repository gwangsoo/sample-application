/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RewardDetailUpdate from './reward-detail-update.vue';
import RewardDetailService from './reward-detail.service';
import AlertService from '@/shared/alert/alert.service';

import RewardService from '@/entities/reward/reward.service';

type RewardDetailUpdateComponentType = InstanceType<typeof RewardDetailUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rewardDetailSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RewardDetailUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('RewardDetail Management Update Component', () => {
    let comp: RewardDetailUpdateComponentType;
    let rewardDetailServiceStub: SinonStubbedInstance<RewardDetailService>;

    beforeEach(() => {
      route = {};
      rewardDetailServiceStub = sinon.createStubInstance<RewardDetailService>(RewardDetailService);
      rewardDetailServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          rewardDetailService: () => rewardDetailServiceStub,
          rewardService: () =>
            sinon.createStubInstance<RewardService>(RewardService, {
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
        const wrapper = shallowMount(RewardDetailUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rewardDetail = rewardDetailSample;
        rewardDetailServiceStub.update.resolves(rewardDetailSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rewardDetailServiceStub.update.calledWith(rewardDetailSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        rewardDetailServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RewardDetailUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.rewardDetail = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rewardDetailServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        rewardDetailServiceStub.find.resolves(rewardDetailSample);
        rewardDetailServiceStub.retrieve.resolves([rewardDetailSample]);

        // WHEN
        route = {
          params: {
            rewardDetailId: '' + rewardDetailSample.id,
          },
        };
        const wrapper = shallowMount(RewardDetailUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.rewardDetail).toMatchObject(rewardDetailSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rewardDetailServiceStub.find.resolves(rewardDetailSample);
        const wrapper = shallowMount(RewardDetailUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});

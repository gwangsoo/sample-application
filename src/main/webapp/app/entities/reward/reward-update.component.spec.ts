/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import RewardUpdate from './reward-update.vue';
import RewardService from './reward.service';
import AlertService from '@/shared/alert/alert.service';

type RewardUpdateComponentType = InstanceType<typeof RewardUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const rewardSample = { id: 'ABC' };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<RewardUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Reward Management Update Component', () => {
    let comp: RewardUpdateComponentType;
    let rewardServiceStub: SinonStubbedInstance<RewardService>;

    beforeEach(() => {
      route = {};
      rewardServiceStub = sinon.createStubInstance<RewardService>(RewardService);
      rewardServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          rewardService: () => rewardServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(RewardUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.reward = rewardSample;
        rewardServiceStub.update.resolves(rewardSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rewardServiceStub.update.calledWith(rewardSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        rewardServiceStub.create.resolves(entity);
        const wrapper = shallowMount(RewardUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.reward = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rewardServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        rewardServiceStub.find.resolves(rewardSample);
        rewardServiceStub.retrieve.resolves([rewardSample]);

        // WHEN
        route = {
          params: {
            rewardId: '' + rewardSample.id,
          },
        };
        const wrapper = shallowMount(RewardUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.reward).toMatchObject(rewardSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        rewardServiceStub.find.resolves(rewardSample);
        const wrapper = shallowMount(RewardUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});

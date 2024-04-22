/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Reward from './reward.vue';
import RewardService from './reward.service';
import AlertService from '@/shared/alert/alert.service';

type RewardComponentType = InstanceType<typeof Reward>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Reward Management Component', () => {
    let rewardServiceStub: SinonStubbedInstance<RewardService>;
    let mountOptions: MountingOptions<RewardComponentType>['global'];

    beforeEach(() => {
      rewardServiceStub = sinon.createStubInstance<RewardService>(RewardService);
      rewardServiceStub.retrieve.resolves({ headers: {} });

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
          rewardService: () => rewardServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        rewardServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(Reward, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(rewardServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.rewards[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: RewardComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Reward, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        rewardServiceStub.retrieve.reset();
        rewardServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        rewardServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeReward();
        await comp.$nextTick(); // clear components

        // THEN
        expect(rewardServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(rewardServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});

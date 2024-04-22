/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import FileInfo from './file-info.vue';
import FileInfoService from './file-info.service';
import AlertService from '@/shared/alert/alert.service';

type FileInfoComponentType = InstanceType<typeof FileInfo>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('FileInfo Management Component', () => {
    let fileInfoServiceStub: SinonStubbedInstance<FileInfoService>;
    let mountOptions: MountingOptions<FileInfoComponentType>['global'];

    beforeEach(() => {
      fileInfoServiceStub = sinon.createStubInstance<FileInfoService>(FileInfoService);
      fileInfoServiceStub.retrieve.resolves({ headers: {} });

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
          fileInfoService: () => fileInfoServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        fileInfoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

        // WHEN
        const wrapper = shallowMount(FileInfo, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(fileInfoServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.fileInfos[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
      });
    });
    describe('Handles', () => {
      let comp: FileInfoComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(FileInfo, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        fileInfoServiceStub.retrieve.reset();
        fileInfoServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        fileInfoServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 'ABC' });

        comp.removeFileInfo();
        await comp.$nextTick(); // clear components

        // THEN
        expect(fileInfoServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(fileInfoServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
